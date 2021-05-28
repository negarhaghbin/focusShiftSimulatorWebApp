function onInputPretestSurvey() {
    const submitBtn = document.getElementById('storePersonalDataButton');
    const userIdInput = document.getElementById("userId");
    const displayHoursInput = document.getElementById("displayHours");

    if (userIdInput.value!="" && (displayHoursInput.value!="" && parseInt(displayHoursInput.value)>-1)) {
        submitBtn.disabled = false;
    } else {
        submitBtn.setAttribute('disabled', true);
    }
}