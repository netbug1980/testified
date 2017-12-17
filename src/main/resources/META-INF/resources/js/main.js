require.config({
    paths: {
        "jquery": "../3rd/jquery",
        "bootstrap": "../3rd/bootstrap.bundle"
    },
    shim: {
        "jquery": {
            exports: '$'
        },
        "bootstrap": {
            deps: ["jquery"]
        }
    }
});
require(["bootstrap"], function(bootstrap) {

});