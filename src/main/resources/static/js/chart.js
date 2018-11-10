/**********************************************************************************/
// CHART SCRIPT

var month = [];
month[0] = "January";
month[1] = "February";
month[2] = "March";
month[3] = "April";
month[4] = "May";
month[5] = "June";
month[6] = "July";
month[7] = "August";
month[8] = "September";
month[9] = "October";
month[10] = "November";
month[11] = "December";


var chartDataSource = {
    mode: "month",
    getPath: function (year, month) {
        if (this.mode === "month") {
            return '/api/analysis/chart/date/' + month + '/' + year;
        }
        else if (this.mode === 'year') {
            return '/api/analysis/chart/year/' + year;
        }
    }
};
/**********************************************************************************/
// OPTION REPLACEMENT MODULE DEFINITION
$.fn.replaceOptions = function (options) {
    var self, $option;

    this.empty();
    self = this;

    $.each(options, function (index, option) {
        $option = $("<option></option>")
            .attr("value", option.value)
            .text(option.text);
        self.append($option);
    });
};

/**********************************************************************************/
// DECLARING ELEMENTS

var dateSelect = $('#dateSelect');
var monthTab = $('#monthTab');
var yearTab = $('#yearTab');

/**********************************************************************************/
// CHART CONFIGURATION

var option = dateSelect.find('option:selected');
var date = option.val().split('-');
var options = {
    width: '100%',
    height: '100px'
};

/**********************************************************************************/
// CHART DRAWING

chart = new Chartist.Bar('.ct-chart', options);

var getData = $.get(chartDataSource.getPath(date[0], date[1]));
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

/**********************************************************************************/
// PAGE OPERATION
dateSelect.change(function () {
    var option = $(this).find('option:selected');
    var date = option.val().split('-');
    var getData = $.get(chartDataSource.getPath(date[0], date[1]));

    getData.done(function (data) {
        data.series = [data.series];
        chart.update(data);
    });

});


monthTab.click(function () {
    yearTab.removeClass('active');
    monthTab.addClass('active');
    var getDates = $.get('/api/analysis/dates');
    getDates.done(function (dates) {
        var options = [];
        dates.forEach(function (date) {
            options.push(
                {
                    text: date[0] + " | " + month[date[1] - 1],
                    value: date[0] + "-" + date[1] + "-" + date[2]
                })
        });
        dateSelect.replaceOptions(options);
        chartDataSource.mode = "month";
        dateSelect.trigger('change');
    })
});


yearTab.click(function () {
    monthTab.removeClass('active');
    yearTab.addClass('active');
    var getYears = $.get('/api/analysis/years');
    getYears.done(function (response) {
        var options = [];
        response.years.forEach(function (year) {
            options.push(
                {
                    text: year.toString(),
                    value: year
                }
            )
        });
        dateSelect.replaceOptions(options);
        chartDataSource.mode = "year";
        dateSelect.trigger('change');
    });

});


/**********************************************************************************/
// EOF
