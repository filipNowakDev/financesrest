var data = {
    // A labels array that can contain any sort of values
    labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri'],
    // Our series array that contains series objects or in this case series data arrays
    series: [
        [5, 2, -4, 2, 0]
    ]
};


var chart = new Chartist.Bar('.ct-chart', data);
var red = '#ff0000';
var blue = '#0000ff';

chart.on('draw', function (context) {
    if (context.type === "bar") {
        if (context.value.y < 0) {
            context.element.attr({
                style: 'stroke: ' + red + '; fill: ' + red + ';'
            });
        }
        else {
            context.element.attr({
                style: 'stroke: ' + blue + '; fill: ' + blue + ';'
            });
        }
    }
});
