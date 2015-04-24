
function onCtMsg(msg) {
    console.log(this);
    if (msg.action === "start") {
        chrome.pageAction.show(this.ctPort.sender.tab.id);
        this.ctPort.postMessage({action: "initializing"});
        //this.svPort.portMessage(msg);
    }
}
;

function onSvMsg(msg) {

}

chrome.runtime.onConnect.addListener(function (port) {
    console.assert(port.name === "eid");

    this.ctPort = port;
    //this.svPort = chrome.runtime.connectNative('net.egelke.eid');

    this.ctPort.onMessage.addListener(this.onCtMsg);
    //this.svPort.onMessage.addListener(this.onSvMsg);

});
