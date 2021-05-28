const WIDTH_RATIO = 0.07;
const HEIGHT_RATIO = 0.06;
const TRIALS = 30;
const OFFSET = 100; // 100 pixels offset

let widthsList = [];
let targetAreaList = [];

let errorCounter = 0;
let counter = 0;
let startTime = Number(0); //maybe cause error then use BigInt()
let durationTime = -1;

let targetWidthMap = {}; //HashMap<Integer, Integer>
let centersMap = {}; //HashMap<Integer, Point>
let errorsMap = {}; //HashMap<Integer, Point>

let targetWidth;
let targetHeight;

let screenSize = new Dimension(screen.width, screen.height);
let screenWidth = screenSize.width;
let screenHeight = screenSize.height;

let calc = new Calculator();
let targetMovement = {}; // HashMap<Integer, Point>
let mouseMovement = {}; // HashMap<Long, Point>
let mouseClicks = {}; // HashMap<Long, Point>

let targetMovementIndex = 0;
let mouseMovementIndex = 0; //maybe cause error then use BigInt()
let mouseClickIndex = 0; //maybe cause error then use BigInt()

let eyeStrainRate = "";

let target;

let dataRes = [];
let htmls = [];

let eyeTrackingAccuracy = 0;

function startEyeTracking(){
    // window.onload = async function() {
    //     await
    // webgazer.setRegression('ridge').begin();
    // webgazer.showPredictionPoints(false)
    //         .showVideoPreview(false);
    // };

    // window.saveDataAcrossSessions = true;
    // window.onbeforeunload = function() {
    //     webgazer.end();
    // };

    webgazer.setGazeListener(function(data) {
        if (data != null){
            let temp = {x: data.x, y: data.y};
            dataRes.push(temp);
        }
    }).resume();

    target.style.display = "block";
    document.addEventListener('click', errorClick);
}

function errorClick(e) {
    if(e.target.tagName != "BUTTON"){
        onClickDocument(e, "Err")
        errorsMap[errorCounter] =  new Point(e.clientX, e.clientY);
        errorCounter++;
    }
}

function stopEyeTracking() {
    webgazer.end();
    document.removeEventListener('click', errorClick);
}

function initGraphicManager(etAccuracy) {
    eyeTrackingAccuracy = etAccuracy;
    createTarget();
    calc.prepareDimensions(screenSize, WIDTH_RATIO, HEIGHT_RATIO, TRIALS);
    startEyeTracking();
}

function captureScreenshot() {
    htmls.push(document.getElementsByTagName('body')[0].innerHTML);
}

function generateTargetWidth() {
    //let width = Math.floor(screenSize.width * WIDTH_RATIO);
    return Math.floor(screen.width * WIDTH_RATIO);
}

function generateTargetHeight() {
    // let height = Math.floor(screenSize.height * HEIGHT_RATIO);
    return Math.floor(screen.height * HEIGHT_RATIO);
}

function getRandomNumber(limit) {
    // var number = rand.nextInt(limit);
    let number = Math.floor(Math.random() * limit); //are they the same?
    if (number < OFFSET) {
        number += OFFSET;
    }
    return number;
}

function getNewX() {
    let limit = screenWidth - (targetWidth + OFFSET);
    return getRandomNumber(limit);
}

function getNewY() {
    let limit = screenHeight - (targetHeight + OFFSET);
    return getRandomNumber(limit);
}

function addNewTargetLocation(p) {
    targetMovement[targetMovementIndex] = p;
    targetMovementIndex++;
}


function sendDataToServer() {

    document.getElementById("finalSurvey").style.display = "none";
    document.getElementById("thankYou").style.display = "block";
    let averageAreaOfTarget = Calculator.getAverageAreaOfTarget(targetAreaList);
    let averageWidths = Calculator.getAverageWidths(widthsList);
    eyeStrainRate = document.getElementById("eyeStrainRate").value;
    closeFullscreen();

    $.post("userStudyFinished",
        {
            eyeTrackingData: JSON.stringify(dataRes),
            durationTime: durationTime,
            eyeStrainRate: eyeStrainRate,
            screenWidth: screenWidth,
            screenHeight: screenHeight,
            averageAreaOfTarget: averageAreaOfTarget,
            averageWidths: averageWidths,
            mouseMovementIndex: mouseMovementIndex,
            errorCounter: errorCounter,
            targetWidthMap: JSON.stringify(targetWidthMap),
            centersMap: JSON.stringify(centersMap),
            errorsMap: JSON.stringify(errorsMap),
            screenshots: JSON.stringify(htmls),
            mv: JSON.stringify(mouseMovement),
            tm: JSON.stringify(targetMovement),
            mouseClicks: JSON.stringify(mouseClicks),
            eyeTrackingAccuracy: eyeTrackingAccuracy,
            counter: counter

        }, function(response, status) {
            console.log(status)
        }
    );

}

function createTarget() {
    document.getElementsByClassName('swal-overlay').item(0).remove();
    target = document.createElement("BUTTON");
    let targetText = document.createTextNode("Click");
    target.appendChild(targetText);
    target.classList.add("target");
    //might provoke problems in terms of size measurement unit (expected pixels)
    targetWidth = generateTargetWidth();
    targetHeight = generateTargetHeight();

    widthsList.push(targetWidth);

    target.style.display = "none";
    target.style.width = targetWidth.toString() + "px";
    target.style.height = targetHeight.toString() + "px";
    target.style.position = "relative";
    target.style.backgroundColor = "#C8E6FF";
    target.addEventListener('click', onClickTarget);

    document.addEventListener('mousemove',onMotionDocument);
    document.body.appendChild(target);

    openFullscreen();

    centersMap[counter] = new Point(screenWidth / 2, screenHeight / 2);

    let areaOfTarget = Calculator.getArea(targetWidth, targetHeight);
    targetAreaList.push(areaOfTarget);
}

function addMousePoint(p) {
    mouseMovement[mouseMovementIndex] = p;
    mouseMovementIndex++;
}

function addMouseClickPoint(p, status){
    let obj = {}
    obj.point = p;
    obj.status = status
    mouseClicks[mouseClickIndex] = obj;
    mouseClickIndex++;
    console.log(mouseClicks)
}

function openFullscreen() {
    let elem = document.documentElement;
    if (elem.requestFullscreen) {
        elem.requestFullscreen();
    } else if (elem.webkitRequestFullscreen) { /* Safari */
        elem.webkitRequestFullscreen();
    } else if (elem.msRequestFullscreen) { /* IE11 */
        elem.msRequestFullscreen();
    }
}

function closeFullscreen() {
    if (document.exitFullscreen) {
        document.exitFullscreen();
    } else if (document.webkitExitFullscreen) { /* Safari */
        document.webkitExitFullscreen();
    } else if (document.msExitFullscreen) { /* IE11 */
        document.msExitFullscreen();
    }
}