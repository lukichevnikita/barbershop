package ru.lukichev.barbershop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lukichev.barbershop.dtos.*;
import ru.lukichev.barbershop.entities.ServiceEntity;
import ru.lukichev.barbershop.entities.WorkDay;
import ru.lukichev.barbershop.entities.WorkHour;
import ru.lukichev.barbershop.entities.WorkWeek;
import ru.lukichev.barbershop.repositories.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class WorkService {

    private final MasterRepository masterRepository;
    private final SerivceRepository serivceRepository;
    private final WorkWeekRepository workWeekRepository;
    private final WorkDaysRepository workDaysRepository;
    private final WorkHourRepository workHourRepository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public void createService(ServiceDto dto) {
        ServiceEntity serviceEntity = ServiceEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .photo(dto.getPhoto())
                .build();

        serivceRepository.save(serviceEntity);
    }

    public List<ServiceDto> getServices() {
        return serivceRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ServiceDto getService(Long id) {
        return mapToDto(serivceRepository.getOne(id));
    }

    private ServiceDto mapToDto(ServiceEntity serviceEntity) {
        return ServiceDto.builder()
                .name(serviceEntity.getName())
                .photo(serviceEntity.getPhoto())
                .price(serviceEntity.getPrice())
                .build();
    }

    public void createWorkWeek() {
        long count = masterRepository.count();
        IntStream.range(0, (int) count)
            .forEach(num -> {
                LocalDate startWeek = getStartWeek(LocalDate.now().plusWeeks(1));

                WorkWeek workWeek = WorkWeek.builder()
                    .startWeek(formatter.format(startWeek))
                    .endWeek(formatter.format(startWeek.plusDays(4)))
                    .build();

                workWeekRepository.save(workWeek);
                createDays(workWeek);
            });
    }

    private void createDays(WorkWeek week) {
        List<WorkDay> days = Stream.of("Понедельник", "Вторник", "Среда", "Четверг", "Пятница")
            .map(day -> WorkDay.builder().day(day).week(week).build())
            .collect(Collectors.toList());

        workDaysRepository.saveAll(days);

        days.forEach(this::createHours);
    }

    private void createHours(WorkDay workDay) {
        List<WorkHour> hours = Stream.of("12:00", "12:30", "13:00", "13:30", "14:00", "14:30")
                .map(time ->
                    WorkHour.builder()
                        .day(workDay)
                        .time(time)
                        .build()
                ).collect(Collectors.toList());

        workHourRepository.saveAll(hours);
    }

    private LocalDate getStartWeek(LocalDate week) {
        return week.minusDays(week.getDayOfWeek().getValue() - 1);
    }

    public List<WorkWeekDto> getCurrentWorkWeeks() {
        List<String> starts = new ArrayList<>();
        LocalDate now = LocalDate.now();
        int dayOfWeek = now.getDayOfWeek().getValue();

        LocalDate startWeek = getStartWeek(dayOfWeek > 4 ? now.plusWeeks(1) : now);
        starts.add(formatter.format(startWeek));
        starts.add(formatter.format(startWeek.plusWeeks(1)));

        return workWeekRepository.findAllByStartWeekIn(starts)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private WorkWeekDto mapToDto(WorkWeek week) {
        return WorkWeekDto.builder()
            .start(week.getStartWeek())
            .end(week.getEndWeek())
            .id(week.getId())
            .days(mapDaysToDto(week.getDays()))
            .build();
    }

    private List<WorkDayDto> mapDaysToDto(List<WorkDay> days) {
        return days.stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    }

    private WorkDayDto mapToDto(WorkDay workDay) {
        return WorkDayDto.builder()
            .id(workDay.getId())
            .day(workDay.getDay())
            .hours(mapHoursToDto(workDay.getHours()))
            .build();
    }

    private List<WorkHourDto> mapHoursToDto(List<WorkHour> hours) {
        return hours.stream()
            .sorted(Comparator.comparing(WorkHour::getTime))
            .map(this::mapToDto)
            .collect(Collectors.toList());
    }

    private WorkHourDto mapToDto(WorkHour workHour) {
        return WorkHourDto.builder()
            .id(workHour.getId())
            .time(workHour.getTime())
            .clientId(workHour.getClientId())
            .masterId(workHour.getMasterId())
            .build();
    }

    public Boolean setClient(RecordDto record) {
        List<String> starts = Collections.singletonList(record.getStartWeekDate());
        List<WorkWeek> allByStartWeekIn = workWeekRepository.findAllByStartWeekIn(starts);

        final Boolean[] result = {null};
        allByStartWeekIn.stream()
            .flatMap(week -> week.getDays().stream())
            .filter(workDay -> workDay.getDay().equals(record.getDay()))
            .flatMap(workDay -> workDay.getHours().stream())
            .filter(workHour -> workHour.getTime().equals(record.getHour()))
            .filter(workHour -> workHour.getClientId() == null)
            .limit(1)
            .findFirst()
            .ifPresentOrElse(workHour -> {
                workHour.setClientId(record.getClient());
                workHour.setMasterId(record.getMaster());
                workHour.setStatus(2);
                workHourRepository.save(workHour);
                result[0] = Boolean.TRUE;
            }, () -> result[0] = Boolean.FALSE);

        return result[0];
    }

    public List<RecordDto> getRecords(Long clientId) {
        return getRecords(clientId, workWeekRepository.findAllByClientId(clientId));
    }

    private int getDayNumber(String day) {
        switch (day) {
            case "Понедельник": return 1;
            case "Вторник": return 2;
            case "Среда": return 3;
            case "Четверг": return 4;
            default: return 5;
        }
    }

    private RecordDto mapToRecordDto(WorkHour hour) {
        String startWeek = hour.getDay().getWeek().getStartWeek();
        String day = hour.getDay().getDay();
        return RecordDto.builder()
            .id(hour.getId())
            .client(hour.getClientId())
            .master(hour.getMasterId())
            .day(day)
            .hour(hour.getTime())
            .startWeekDate(getRecordDate(startWeek, day))
            .statusId(hour.getStatus())
            .build();
    }

    private String getRecordDate(String date, String day) {
        return formatter.format(LocalDate.parse(date, formatter).plusDays(getDayNumber(day) - 1));
    }

    public List<RecordDto> getRecordsForMaster(Long id) {
        return getRecordsForMaster(id, workWeekRepository.findAllByMasterId(id));
    }

    private List<RecordDto> getRecords(Long id, List<WorkWeek> weeks) {
        return weeks.stream()
                .sorted(Comparator.comparing(WorkWeek::getStartWeek))
                .flatMap(week -> week.getDays().stream())
                .sorted(Comparator.comparingInt(el -> getDayNumber(el.getDay())))
                .flatMap(workDay -> workDay.getHours().stream())
                .sorted(Comparator.comparing(WorkHour::getTime))
                .filter(workHour -> id.equals(workHour.getClientId()))
                .distinct()
                .map(this::mapToRecordDto)
                .collect(Collectors.toList());
    }

    private List<RecordDto> getRecordsForMaster(Long id, List<WorkWeek> weeks) {
        return weeks.stream()
                .sorted(Comparator.comparing(WorkWeek::getStartWeek))
                .flatMap(week -> week.getDays().stream())
                .sorted(Comparator.comparingInt(el -> getDayNumber(el.getDay())))
                .flatMap(workDay -> workDay.getHours().stream())
                .sorted(Comparator.comparing(WorkHour::getTime))
                .filter(workHour -> id.equals(workHour.getMasterId()))
                .distinct()
                .map(this::mapToRecordDto)
                .collect(Collectors.toList());
    }

    public void checkRecord(Map<String, Long> props) {
        WorkHour hour = workHourRepository.getOne(props.get("id"));
        hour.setStatus(props.get("status").intValue());
        workHourRepository.save(hour);
    }
}
