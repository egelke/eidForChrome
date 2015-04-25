
function onNtMsg(msg) {
    console.log('eid-bg: native message');
    console.dir(msg);
    
    this.ctPort.postMessage(msg);
}

function onCtMsg(msg) {
    console.log('eid-bg: content message');
    console.dir(msg);

    if (msg.action === "start") {
        chrome.pageAction.show(this.ctPort.sender.tab.id);

        this.svPort = chrome.runtime.connectNative('net.egelke.chrome.eid.native');
        this.svPort.onMessage.addListener(onNtMsg);
    }
    
    this.svPort.postMessage(msg);
}


chrome.runtime.onConnect.addListener(function (port) {
    console.assert(port.name === "eid");

    this.ctPort = port;
    this.ctPort.onMessage.addListener(onCtMsg);
});

