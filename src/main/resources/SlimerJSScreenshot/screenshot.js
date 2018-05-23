var system = require('system');
var url = system.args[1];
var screenshotFile = system.args[2] || 'screenshot.png';

var webpage = require('webpage').create();
webpage
  .open(url)
  .then(function(){
    webpage.viewportSize = { width: 1366, height: 768 };
    webpage.render(screenshotFile, { onlyViewport: true });
    slimer.exit()
  });

