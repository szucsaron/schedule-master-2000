


function initDrag() {
    
    
        /* Events fired on the drag target */
    document.addEventListener("dragstart", function(event) {
        event.dataTransfer.setData("Text", event.target.id);
        
    });

    /* Events fired on the drop target */
    document.addEventListener("dragover", function(event) {
        event.preventDefault();
    });
    
    document.addEventListener("drop", function(event) {
        event.preventDefault();
        // var data = event.dataTransfer.getData("Text");
        // event.target.appendChild(document.getElementById(data));

    });
}

function onDrag(element) {
    console.log('drag');
}


