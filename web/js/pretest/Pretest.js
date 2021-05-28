let userId = "";
let displayHours = "";
let symptoms = [];
let symptomsCount = 9;

function storePersonalData(){
    userId = document.getElementById("userId").value;
    displayHours = document.getElementById("displayHours").value;
    symptoms = getSymptoms();
    $.post("PretestServlet", {
        userId: userId,
        displayHours: displayHours,
        symptoms: JSON.stringify(symptoms)
    },function(response, status) {
        console.log(status)
    });
    document.getElementById("personalDataDiv").remove();
    document.getElementById("userStudyDiv").style.display = "block";


}

function getSymptoms(){
    let result = [];
    for (let i=1;i<symptomsCount;i++){
        if (document.getElementById("symptoms"+i).checked){
           result.push(document.getElementById("symptoms"+i).value)
        }
    }
    let otherValues = document.getElementById("symptoms10").value;
    if ( otherValues != ""){
        result.push(otherValues)
    }
    return result
}
