// script.js

// Add event listener to the city search input field
document.getElementById('citySearch').addEventListener('input', function() {
  const city = this.value;
  const url = '/owners/find/city?city=' + city;

  // Fetch data from the server and update the owner list
  fetch(url)
    .then(response => response.text())
    .then(data => {
      document.getElementById('ownerList').innerHTML = data;
    });
});

// Add event listener to the clear button
document.getElementById('clearCitySearch').addEventListener('click', function() {
  document.getElementById('citySearch').value = '';
  const url = '/owners';

  // Fetch data from the server and update the owner list
  fetch(url)
    .then(response => response.text())
    .then(data => {
      document.getElementById('ownerList').innerHTML = data;
    });
});
