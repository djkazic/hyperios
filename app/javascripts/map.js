mapboxgl.accessToken = 'pk.eyJ1IjoibGR1bW91bGluIiwiYSI6ImNpZzc0cmVyaDBmb2N1Y2t0YWowN3NuaGUifQ.4gtUrXuSh0OSAyipVBkF6Q';
var map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/dark-v8',
    center: [-71.0589, 42.3601],
    zoom: 13
});

map.addControl(new mapboxgl.Geocoder());