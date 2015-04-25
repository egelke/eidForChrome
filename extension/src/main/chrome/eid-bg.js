
function onClose() {
    console.log("Disconnected");
    this.svPort = null;
}

function onNtMsg(msg) {
    console.log('eid-bg: forward output');
    console.dir(msg);
    
    this.ctPort.postMessage(msg);
}

function onCtMsg(msg) {
    console.log('eid-bg: forward input');
    console.dir(msg);

    if (msg.action === "start" && this.svPort == null) {
        chrome.pageAction.show(this.ctPort.sender.tab.id);

        this.svPort = chrome.runtime.connectNative('net.egelke.chrome.eid.native');
        this.svPort.onMessage.addListener(onNtMsg);
        this.svPort.onDisconnect.addListener(onClose);
    }
    console.assert(this.svPort != null, "No native program active");
    
    this.svPort.postMessage(msg);
}


chrome.runtime.onConnect.addListener(function (port) {
    console.assert(port.name === "eid");

    this.ctPort = port;
    this.ctPort.onMessage.addListener(onCtMsg);
});



