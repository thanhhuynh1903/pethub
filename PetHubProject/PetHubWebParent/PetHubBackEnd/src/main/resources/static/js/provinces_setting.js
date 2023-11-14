var buttonLoad4Provinces;
var dropDownCountry4Provinces;
var dropDownProvinces;
var buttonAddProvince;
var buttonUpdateProvince;
var buttonDeleteProvince;
var labelProvinceName;
var fieldProvinceName;

$(document).ready(function() {
	buttonLoad4Provinces = $("#buttonLoadCountriesForProvinces");
	dropDownCountry4Provinces = $("#dropDownCountriesForProvinces");
	dropDownProvinces = $("#dropDownProvinces");
	buttonAddProvince = $("#buttonAddProvince");
	buttonUpdateProvince = $("#buttonUpdateProvince");
	buttonDeleteProvince = $("#buttonDeleteProvince");
	labelProvinceName = $("#labelProvinceName");
	fieldProvinceName = $("#fieldProvinceName");
	
	buttonLoad4Provinces.click(function() {
		loadCountries4Provinces();
	});
	
	dropDownCountry4Provinces.on("change", function() {
		loadProvinces4Country();
	});

	dropDownProvinces.on("change", function() {
		changeFormProvinceToSelectedProvince();
	});
		
	buttonAddProvince.click(function() {
		if (buttonAddProvince.val() == "Add") {
			addProvince();
		} else {
			changeFormProvinceToNew();
		}
	});
	
	buttonUpdateProvince.click(function() {
		updateProvince();
	});
	
	buttonDeleteProvince.click(function() {
		deleteProvince();
	});
});

function deleteProvince() {
	ProvinceId = dropDownProvinces.val();
	
	url = contextPath + "provinces/delete/" + ProvinceId;
	
	$.ajax({
		type: 'DELETE',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function() {
		$("#dropDownProvinces option[value='" + ProvinceId + "']").remove();
		changeFormProvinceToNew();
		showToastMessage("The Province has been deleted");
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to server or server encountered an error");
	});		
}

function updateProvince() {
	if (!validateFormProvince()) return;
	
	url = contextPath + "provinces/save";
	ProvinceId = dropDownProvinces.val();
	ProvinceName = fieldProvinceName.val();
	
	selectedCountry = $("#dropDownCountriesForProvinces option:selected");
	countryId = selectedCountry.val();
	countryName = selectedCountry.text();
	
	jsonData = {id: ProvinceId, name: ProvinceName, country: {id: countryId, name: countryName}};
	
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(ProvinceId) {
		$("#dropDownProvinces option:selected").text(ProvinceName);
		showToastMessage("The Province has been updated");
		changeFormProvinceToNew();
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to server or server encountered an error");
	});	
}

function addProvince() {
	if (!validateFormProvince()) return;
	
	url = contextPath + "provinces/save";
	ProvinceName = fieldProvinceName.val();
	
	selectedCountry = $("#dropDownCountriesForProvinces option:selected");
	countryId = selectedCountry.val();
	countryName = selectedCountry.text();
	
	jsonData = {name: ProvinceName, country: {id: countryId, name: countryName}};
	
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(ProvinceId) {
		selectNewlyAddedProvince(ProvinceId, ProvinceName);
		showToastMessage("The new Province has been added");
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to server or server encountered an error");
	});
		
}

function validateFormProvince() {
	formProvince = document.getElementById("formProvince");
	if (!formProvince.checkValidity()) {
		formProvince.reportValidity();
		return false;
	}	
	
	return true;
}

function selectNewlyAddedProvince(ProvinceId, ProvinceName) {
	$("<option>").val(ProvinceId).text(ProvinceName).appendTo(dropDownProvinces);
	
	$("#dropDownProvinces option[value='" + ProvinceId + "']").prop("selected", true);
	
	fieldProvinceName.val("").focus();
}

function changeFormProvinceToNew() {
	buttonAddProvince.val("Add");
	labelProvinceName.text("Province Name:");
	
	buttonUpdateProvince.prop("disabled", true);
	buttonDeleteProvince.prop("disabled", true);
	
	fieldProvinceName.val("").focus();	
}

function changeFormProvinceToSelectedProvince() {
	buttonAddProvince.prop("value", "New");
	buttonUpdateProvince.prop("disabled", false);
	buttonDeleteProvince.prop("disabled", false);
	
	labelProvinceName.text("Selected Province/Province:");
	
	selectedProvinceName = $("#dropDownProvinces option:selected").text();
	fieldProvinceName.val(selectedProvinceName);
	
}

function loadProvinces4Country() {
	selectedCountry = $("#dropDownCountriesForProvinces option:selected");
	countryId = selectedCountry.val();
	url = contextPath + "provinces/list_by_country/" + countryId;
	
	$.get(url, function(responseJSON) {
		dropDownProvinces.empty();
		
		$.each(responseJSON, function(index, Province) {
			$("<option>").val(Province.id).text(Province.name).appendTo(dropDownProvinces);
		});
		
	}).done(function() {
		changeFormProvinceToNew();
		showToastMessage("All Provinces have been loaded for country " + selectedCountry.text());
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to server or server encountered an error");
	});	
}

function loadCountries4Provinces() {
	url = contextPath + "countries/list";
	$.get(url, function(responseJSON) {
		dropDownCountry4Provinces.empty();
		
		$.each(responseJSON, function(index, country) {
			$("<option>").val(country.id).text(country.name).appendTo(dropDownCountry4Provinces);
		});
		
	}).done(function() {
		buttonLoad4Provinces.val("Refresh Country List");
		showToastMessage("All countries have been loaded");
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to server or server encountered an error");
	});
}