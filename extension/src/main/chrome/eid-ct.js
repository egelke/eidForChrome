
var Eid = (function () {

    function Eid () {
        this.port = chrome.runtime.connect({name: "eid"});
        this.port.onMessage.addListener(this.bgMsg);
    }
    
    Eid.prototype.bgMsg = function (msg) {
        console.log('eid-ct: Background message');
        console.dir(msg); 
        
        if (msg.action === 'UI') {
            //TODO
        }
        window.postMessage(msg, "*");
    };
    
    Eid.prototype.pgMsg = function (msg) {
        console.log('eid-ct: Page message');
        console.dir(msg);
        
        this.port.postMessage(msg);
    };
    
    
    return Eid;

})();

function pgEvent(event) {
    if (event.source !== window || event.data.type !== 'eid-req') return;
    
    eid.pgMsg(event.data);
}

var eid;
var e = document.getElementById("eid");
if (e !== null) {
    eid = new Eid();
    window.addEventListener("message", pgEvent, false);
    this.e.innerHTML = "ready";
};
