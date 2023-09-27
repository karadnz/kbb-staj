async function fetchSokakInfo(sId, eId, btnElement) {
    // Check if data is already loaded
    if (btnElement.getAttribute('data-loaded') === 'true') {
        return;
    }

    console.log(sId);
    console.log(eId);

    const response = await fetch(`http://localhost:8093/address/sokaklar/${sId}`);
    const data = await response.json();
    console.log(data);

    const dropdownElement = document.getElementById(`sokakInfo_${eId}`);

    // populate the dropdown
    let content = `
        <a class="dropdown-item">Il Adi: ${data.ilAdi}</a>
        <a class="dropdown-item">Ilce Adi: ${data.ilceAdi}</a>
        <a class="dropdown-item">Mahalle Adi: ${data.mahalleAdi}</a>
        <a class="dropdown-item">Sokak Adi: ${data.sokakAdi}</a>   
    `;

    const dropdownMenu = dropdownElement.querySelector('.dropdown-menu');
    dropdownMenu.innerHTML = content;

    // Mark as loaded to avoid fetching data again
    btnElement.setAttribute('data-loaded', 'true');
}


/*document.addEventListener('DOMContentLoaded', function() {
    const dropdownButtons = document.querySelectorAll('.fetchSokakInfoOnClick');

    dropdownButtons.forEach(button => {
        button.addEventListener('click', function() {
            const sId = this.closest('tr').querySelector('td[data-sid]').dataset.sid; // assuming you've added data-sid attribute to the td element
            const eId = this.closest('tr').querySelector('td[data-eid]').dataset.eid; // assuming you've added data-eid attribute to the td element

            fetchSokakInfo(sId, eId, this);
        });
    });
});*/

document.addEventListener('DOMContentLoaded', function() {
    const detailButtons = document.querySelectorAll('.fetchSokakInfoOnClick');

    // Initialize the Leaflet map
    const map = L.map('map').setView([0, 0], 2);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors'
    }).addTo(map);

    detailButtons.forEach(button => {
        button.addEventListener('click', async function() {
            const personId = this.getAttribute('data-person-id');

            // Fetch user details by personId
            const userDetails = await fetchUserDetails(personId);

            const eid = userDetails.eid;
            document.getElementById('editButton').href = `/editUser/${eid}`;
            document.getElementById('deleteButton').setAttribute('data-eid', eid); // store the eid in the delete button for use in the confirmDelete function

            const detailedUserInfo = document.getElementById('detailedUserInfo');
// Now that we have structured HTML, we can directly target the span elements to fill the details
            document.getElementById('detailEid').innerText = userDetails.eid;
            document.getElementById('detailUsername').innerText = userDetails.username;
            document.getElementById('detailName').innerText = userDetails.name;
            document.getElementById('detailEmail').innerText = userDetails.email;
            document.getElementById('detailActive').innerText = userDetails.enable;
            document.getElementById('detailSid').innerText = userDetails.s_id;
// Populate other fields similarly

            //for roles
            const rolesList = document.getElementById('detailRoles');
            rolesList.innerHTML = '';

            // Add each role to the roles list
            userDetails.roles.forEach(role => {
                const roleItem = document.createElement('li');
                roleItem.innerText = role.name;
                rolesList.appendChild(roleItem);
            });


            // Fetch the address info and update the map
            const sId = userDetails.s_id;
            const response = await fetch(`http://localhost:8093/address/sokaklar/${sId}`);
            const data = await response.json();

            // Fetch coordinates and update the map
            const coordData = await fetchCoordinates(data);
            if (coordData && coordData.lat && coordData.lng) {
                map.setView([coordData.lat, coordData.lng], 13);
                L.marker([coordData.lat, coordData.lng]).addTo(map)
                    .bindPopup(`<b>${userDetails.name}, ${data.mahalleAdi}, ${data.ilceAdi}, ${data.ilAdi}</b>`)
                    .openPopup();
            }
        });
    });
});

// Add this function to fetch user details
async function fetchUserDetails(personId) {
    // Replace the URL with the correct endpoint to fetch user details by personId
    const response = await fetch(`http://localhost:8092/utils/userDetail/${personId}`, {
        headers: {
            'Authorization': `Bearer ${userToken}`
        }
    });
    return await response.json();
}



function confirmDelete() {
    const eId = document.getElementById('deleteButton').getAttribute('data-eid');
    const confirmation = confirm('Are you sure you want to delete this user?');

    if (confirmation) {
        // If the user clicked "OK", proceed to delete the user
        window.location.href = `/delete?id=${eId}`;
        return true;
    } else {
        // If the user clicked "Cancel", do nothing
        return false;
    }
}

//map
async function fetchCoordinates2(sId) {
    const response = await fetch(`http://localhost:8093/address/sokaklar/${sId}`);
    const data = await response.json();
    const query = `${data.mahalleAdi} ${data.ilceAdi}`;
    const akey = "38f258cf22a0dcdb8837871bf902ab86";
    const region = data.ilAdi;

    const url = `http://api.positionstack.com/v1/forward?access_key=${akey}&query=${query}&limit=1&region=${region}`;

    const coordResponse = await fetch(url);
    const coordData = await coordResponse.json();

    return coordData.data[0];
}

async function fetchCoordinates(data) {
    const query = encodeURIComponent(`${data.sokakAdi}, ${data.mahalleAdi}, ${data.ilceAdi}, ${data.ilAdi}`);
    const akey = "AIzaSyBkVOYEJFa6tMwVW2-CePJK-_3nh7bRbPs";
    const url = `https://maps.googleapis.com/maps/api/geocode/json?address=${query}&key=${akey}`;

    const coordResponse = await fetch(url);
    const coordData = await coordResponse.json();

    if (coordData.status === 'OK') {
        return coordData.results[0].geometry.location;
    }

    return null;
}
