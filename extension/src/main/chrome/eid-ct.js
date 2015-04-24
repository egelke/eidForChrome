
var Eid = (function () {

    function Eid (element) {
        this.e = element;
        this.port = chrome.runtime.connect({name: "eid"});
        
        this.port.onMessage.addListener(this.onMsg);
        this.port.postMessage({
            action: "start",
            params: {}
        });
    }
    
    Eid.prototype.onMsg = function (msg) {
        if (msg.action === "initializing") {
            this.e.innerHTML = "<span>initializing...</span>";
        }
    };
    
    
    return Eid;

})();

var e = document.getElementById("eid");
if (e !== null) {
    var eid = new Eid(e);
};
