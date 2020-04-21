const path = require('path');
const VueLoaderPlugin = require('vue-loader/lib/plugin');

module.exports = {
    entry: {
        main: path.join(__dirname, 'src', 'main', 'resources', 'js', 'main.js'),
        auth: path.join(__dirname, 'src', 'main', 'resources', 'js', 'auth.js')
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /(node_modules|bower_components)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env']
                    }
                }
            },
            {
                test: /\.vue$/,
                use: [
                    {
                        loader: 'vue-loader'
                    }
                ]
            },
            {
                test: /\.scss$|\.css$/,
                use: [
                    'vue-style-loader',
                    'css-loader',
                    'sass-loader'
                ]
            }
        ]
    },
    plugins: [
        new VueLoaderPlugin()
    ],
    resolve: {
        modules: [
            path.join(__dirname, 'src', 'main', 'resources', 'js'),
            path.join(__dirname, 'src', 'main', 'resources', 'styles'),
            path.join(__dirname, 'node_modules'),
        ],
    }
};
