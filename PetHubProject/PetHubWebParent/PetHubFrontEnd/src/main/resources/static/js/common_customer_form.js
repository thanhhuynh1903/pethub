var dropDownCountry;
var dataListProvince;
var fieldProvince;

$(document).ready(function () {
	dropDownCountry = $("#country");
	dataListProvince = $("#listProvinces");
	fieldProvince = $("#province");

	dropDownCountry.on("change", function () {
		loadProvincesForCountry();
		fieldProvince.val("").focus();
	});
});

function loadProvincesForCountry() {
	selectedCountry = $("#country option:selected");
	countryId = selectedCountry.val();
	url = contextPath + "settings/list_provinces_by_country/" + countryId;

	$.get(url, function (responseJSON) {
		dataListProvince.empty();

		$.each(responseJSON, function (index, province) {
			$("<option>").val(province.name).text(province.name).appendTo(dataListProvince);
		});

	}).fail(function () {
		alert('failed to connect to the server!');
	});
}

function checkPasswordMatch(confirmPassword) {
	if (confirmPassword.value != $("#password").val()) {
		confirmPassword.setCustomValidity("Passwords do not match!");
	} else {
		confirmPassword.setCustomValidity("");
	}
}

function checkPasswordMatch1(confirmPassword) {
	var newPassword = document.getElementById('newPassword');
	if (confirmPassword.value != newPassword.value) {
		confirmPassword.setCustomValidity("Passwords do not match!");
	} else {
		confirmPassword.setCustomValidity("");
	}
}
