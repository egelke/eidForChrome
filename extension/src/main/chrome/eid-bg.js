
var application = 'net.egelke.chrome.eid.native';

function onClose() {
    console.log("Disconnected");
    this.svPort = null;
}

function onNtMsg(msg) {
    console.log('eid-bg: forward output');
    console.dir(msg);
    
    this.ctPort.postMessage(msg);
}

/*
function log(msg) {
    console.log('eid-bg: log');
    console.dir(msg);
}
*/

function onCtMsg(msg) {
    console.log('eid-bg: forward input');
    console.dir(msg);

    if (msg.action.type === "START" && this.svPort == null) {
        chrome.pageAction.show(this.ctPort.sender.tab.id);

        this.svPort = chrome.runtime.connectNative(application);
        this.svPort.onMessage.addListener(onNtMsg);
        this.svPort.onDisconnect.addListener(onClose);
    }
    console.assert(this.svPort != null, "No native program active");
    
    this.svPort.postMessage(msg);
    //chrome.runtime.sendNativeMessage(application, msg, log);
}


chrome.runtime.onConnect.addListener(function (port) {
    console.assert(port.name === "eid");

    this.ctPort = port;
    this.ctPort.onMessage.addListener(onCtMsg);
});



