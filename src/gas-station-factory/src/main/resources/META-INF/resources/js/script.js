	const map = L.map('map').setView([48.0009364344, 2.5175567361], 6);
	
	L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
	  attribution:
	    '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
	}).addTo(map);
	
	const url = '/api/factory/list';
	
	async function fetchStationJSON() {
	  const response = await fetch(url);
	  if (!response.ok) {
	    const message = `An error has occured: ${response.status}`;
	    throw new Error(message);
	  }
	  const gs = await response.json();
	  
	  //TODO log json
  	  console.log(gs)
	  return gs;
	}
	
	fetchStationJSON().then(jsonData => {
	    // Itérer sur le tableau
	    const nb = 1;
	    for (const station of jsonData) {
	      // Accéder à l'attribut "latlng" de chaque station
	      const latlng = station.latlng.split(',');
		 
	      // Afficher la position de la station
	      console.log('Station',nb,":", latlng[0], ",", latlng[1]);

		const generateHtmlTable = (data) => {
    const tableRows = data.tanks.map(tank => {
        const row = `
            <div class="row">
                <div class="cell">${tank.fuel}</div>
                <div class="cell">${tank.capacity}</div>
                <div class="cell">Level ${tank.sensors.level}%, Temperature ${tank.sensors.temperature}°C, Humidity ${tank.sensors.humidity}%</div>
            </div>
        `;
        return row;
    }).join('');

    const htmlTable = `
        <div class="table">
            <div class="row header">
                <div class="cell">Fuel</div>
                <div class="cell">Capacity</div>
                <div class="cell">Sensors</div>
            </div>
            ${tableRows}
        </div>
    `;

    return htmlTable;
};

const fuelTableHtml = `	<div><b>Adresse : </b>${station.adresse} ${station.codepostal} ${station.commune}</div>` + generateHtmlTable(station);

		
	//Placer un marker
	const marker  = L.marker([latlng[0],latlng[1],])
		.bindPopup(fuelTableHtml,{ maxWidth : 560 })
		.bindTooltip("Station "+station.id)
		.addTo(map);
		}
	})
	.catch(error => {
		console.error('Erreur lors de la récupération des données:', error);
	});
	  