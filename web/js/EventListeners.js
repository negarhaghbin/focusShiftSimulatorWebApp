function onInputEyeStrainRateButton(e) {
    const submitBtn = document.getElementById('sendDataToServer');
    const eyeStrainRateInput = document.getElementById('eyeStrainRate');
    if (eyeStrainRateInput.value!="" & (0<parseInt(eyeStrainRateInput.value) & parseInt(eyeStrainRateInput.value)<6)) {
        submitBtn.disabled = false;
    } else {
        submitBtn.setAttribute('disabled', true);
    }
}

function onMotionDocument(e) {
    let x = e.clientX;
    let y = e.clientY;
    let p = new Point(x, y);
    addMousePoint(p);

}

function onClickTarget(e) {
    event.stopPropagation();
    if (counter < TRIALS) {
        captureScreenshot(counter + 1);
        if (startTime == 0) {
            let d = new Date();
            startTime = d.getTime();
        }
        targetWidthMap[counter] = targetWidth;

        let dim = calc.pickDimension();
        let newWidth = dim.width;
        targetWidth = newWidth;
        widthsList.push(newWidth);

        let newHeight = dim.height;
        targetHeight = newHeight;

        e.target.style.width = newWidth.toString() + "px";
        e.target.style.height = newHeight.toString() + "px";

        let areaOfTarget = calc.getArea(newWidth, newHeight);
        targetAreaList.push(areaOfTarget);

        let newX = getNewX();
        let newY = getNewY();
        let newTargetLocation = new Point(newX, newY);
        e.target.style.left = newX.toString()+"px";
        e.target.style.top = newY.toString()+"px";

        addNewTargetLocation(newTargetLocation);
        let centerPoint = calc.getTargetCenter(e.target);

        counter++;
        centersMap[counter] = centerPoint;
    }
    else {
        let d = new Date();
        let stopTime = d.getTime(); //may cause error then use BigInt()
        durationTime = (stopTime - startTime) / 1000;
        stopEyeTracking();
        target.style.display = "none";
        document.getElementById("finalSurvey").style.display = "block";
    }
}