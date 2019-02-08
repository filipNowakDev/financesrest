$('#entryTable').DataTable({
    responsive: true,
    ajax: {
        url: entry_datasource,
        dataSrc: ''
    },
    columns: [
        {
            data: 'description'
        },
        {
            data: 'category.name'
        },
        {
            data: 'date',
            render: "[/]"

        },
        {
            data: 'value'
        },
        {
            //actions
            data: 'id',
            render: function (data, type, row) {
                return '<div class="btn-group">' +
                    '<form method="get" action="/entries/edit/' + data + '">' +
                    '<button type="submit" class="btn btn-primary active">Edit</button>' +
                    '</form>' +
                    '<form method="post" action="/entries/delete/' + data + '">' +
                    '<button type="submit" class="btn btn-danger active">Delete</button>' +
                    '<input name="_csrf" value="' + $("meta[name='_csrf']").attr("content") + '" type="hidden">' +
                    '</form>' +
                    '</div>';
            }
        }
    ]
});



