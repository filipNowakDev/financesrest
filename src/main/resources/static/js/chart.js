var option = $('#dateSelect').find('option:selected');
var date = option.val().split('-');


var options = {
    width: '100%',
    height: '400px'
};

chart = new Chartist.Bar('.ct-chart', options);

var getData = $.get('/api/analysis/chart/date/' + date[1] + '/' + date[0]);
getData.done(function (data) {
    data.series = [data.series];
    chart.update(data);
});






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

$('#dateSelect').change(function () {


    var option = $(this).find('option:selected');
    var date = option.val().split('-');
    var getData = $.get('/api/analysis/chart/date/' + date[1] + '/' + date[0]);

    getData.done(function (data) {
        data.series = [data.series];
        chart.update(data);
    });

});
