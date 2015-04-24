
function onSvMsg(msg) {

}

function onCtMsg(msg) {
    console.log('processing content message');
    console.dir(msg);
    
    if (msg.action === "start") {
        chrome.pageAction.show(this.ctPort.sender.tab.id);
        this.ctPort.postMessage({action: "initializing"});
        
        this.svPort = chrome.runtime.connectNative('net.egelke.chrome.eid.native');
        this.svPort.onMessage.addListener(onSvMsg);

        this.svPort.postMessage(msg);
    }
}

//Listen to onConnect. Warnin! Run in content scope
chrome.runtime.onConnect.addListener(function (port) {
    console.assert(port.name === "eid");

    this.ctPort = port;
    this.ctPort.onMessage.addListener(onCtMsg);
    //this.svPort.onMessage.addListener(this.onSvMsg);

});

