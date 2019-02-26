function loadYears() {
    $.get("/poems/years", function (data) {
        let yearSelector = $("#years");
        for (let year of data) {
            yearSelector.append("<option value='" + year + "'>" + year + "</option>")
        }
    });
}