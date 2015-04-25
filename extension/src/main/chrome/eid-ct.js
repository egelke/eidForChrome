
var Eid = (function () {

    function Eid () {
        this.port = chrome.runtime.connect({name: "eid"});
        this.port.onMessage.addListener(this.bgMsg);
        
        this.port.postMessage({
            type: 'eid-req', 
            action: 'start'
        });
    }
    
    Eid.prototype.bgMsg = function (msg) {
        console.log('eid-ct: forward output');
        console.dir(msg); 

        if (msg.action === 'end') {
            this.port.disconnect();
            this.port = null;
        } else {
            if (msg.action === 'UI') {
                //TODO
            } 
            window.postMessage(msg, "*");
        }
    };
    
    Eid.prototype.pgMsg = function (msg) {
        console.log('eid-ct: forward input');
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
