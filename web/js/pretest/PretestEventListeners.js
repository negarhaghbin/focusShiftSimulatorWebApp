function onInputPretestSurvey() {
    // const invalidForm = document.querySelector('input:invalid');
    const submitBtn = document.getElementById('storePersonalDataButton');

    const userIdInput = document.getElementById("userId");
    const displayHoursInput = document.getElementById("displayHours");
    if (userIdInput.value!="" & displayHoursInput.value!="") {
        submitBtn.disabled = false;
    } else {
        submitBtn.setAttribute('disabled', true);
    }
}