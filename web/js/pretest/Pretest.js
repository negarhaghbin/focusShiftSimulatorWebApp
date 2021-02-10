let userId = "";
let displayHours = "";

function storePersonalData(){
    userId = document.getElementById("userId").value;
    displayHours = document.getElementById("displayHours").value;
    $.post("PretestServlet", {
        userId: userId,
        displayHours: displayHours
    },function(response, status) {
        console.log(status)
    });
    document.getElementById("personalDataDiv").remove();
    document.getElementById("userStudyDiv").style.display = "block";
}

// export {userId, displayHours};