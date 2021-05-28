class Calculator {

    constructor(){
        this.dimensionsList = []
    }

    static getArea(width, height) {
        return width * height;
    }

    static getTargetCenter(target) {
        let widthString = target.style.width;
        let width =  parseInt(widthString.substring(0,widthString.length-2));

        let heightString = target.style.height;
        let height = parseInt(heightString.substring(0,heightString.length-2));

        let x1String = target.style.left;
        let x1 = parseInt(x1String.substring(0,x1String.length-2));

        let y1String = target.style.top;
        let y1 = parseInt(y1String.substring(0,y1String.length-2));

        let x2 = x1 + width;
        let y2 = y1 + height;

        let midX = Math.floor((x1 + x2) / 2);
        let midY = Math.floor((y1 + y2) / 2);
        // console.log(point);

        return new Point(midX, midY);
    }

    /*calcDistance(x1, y1, x2, y2) {
        let deltaX = x2 - x1;
        let deltaY = y2 - y1;

        let valueX = Math.floor(Math.pow(deltaX, 2));
        let valueY = Math.floor(Math.pow(deltaY, 2));
        return Math.sqrt(valueX + valueY);
    }*/



    prepareDimensions(screenSize, widthRatio, heightRatio, count) {
        let initialWidth = Math.floor(screenSize.width * widthRatio);
        let initialHeight = Math.floor(screenSize.height * heightRatio);

        for (let i = 0; i < count; i++) {
            let width = Math.floor(initialWidth + (widthRatio * initialWidth));
            let height = Math.floor(initialHeight + (heightRatio * initialHeight));

            initialWidth = width;
            initialHeight = height;

            let dim = new Dimension(width, height);
            this.dimensionsList.push(dim);
        }
    }

    pickDimension() {
        let limit = this.dimensionsList.length;
        let index = Calculator.getRandomNumber(limit);

        let dim = this.dimensionsList[index];
        this.dimensionsList.splice(index,1);
        //might return undefined. double check#########
        return dim;
    }

    static getRandomNumber(limit) {
        // let number = rand.nextInt(limit);
        return Math.floor((Math.random() * limit));  //are they the same?
    }

    static getAverage(valueList) {
        let sum = 0.0;
        let item;
        for (item of valueList) {
            sum += item;
        }
        return (sum / valueList.length); //should be double
    }

    static getAverageAreaOfTarget(areas) {
        return Calculator.getAverage(areas);
    }


    static getAverageWidths(widthsList) {
        return Calculator.getAverage(widthsList);
    }

}
