var dropdownCountries;
var dropdownProvinces;

$(document).ready(function() {
	dropdownCountries = $("#country");
	dropdownProvinces = $("#listProvinces");

	dropdownCountries.on("change", function() {
		loadProvinces4Country();
		$("#Province").val("").focus();
	});	
	
	loadProvinces4Country();
});

function loadProvinces4Country() {
	selectedCountry = $("#country option:selected");
	countryId = selectedCountry.val();
	
	url = contextPath + "provinces/list_by_country/" + countryId;
	
	$.get(url, function(responseJson) {
		dropdownProvinces.empty();
		
		$.each(responseJson, function(index, Province) {
			$("<option>").val(Province.name).text(Province.name).appendTo(dropdownProvinces);
		});
	}).fail(function() {
		showErrorModal("Error loading Provinces/provinces for the selected country.");
	})	
}	