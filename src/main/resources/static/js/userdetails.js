async function fetchSokakInfo(sId, eId, btnElement) {
    // Check if data is already loaded
    if (btnElement.getAttribute('data-loaded') === 'true') {
        return;
    }

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


document.addEventListener('DOMContentLoaded', function() {
    const dropdownButtons = document.querySelectorAll('.fetchSokakInfoOnClick');

    dropdownButtons.forEach(button => {
        button.addEventListener('click', function() {
            const sId = this.closest('tr').querySelector('td[data-sid]').dataset.sid; // assuming you've added data-sid attribute to the td element
            const eId = this.closest('tr').querySelector('td[data-eid]').dataset.eid; // assuming you've added data-eid attribute to the td element

            fetchSokakInfo(sId, eId, this);
        });
    });
});


function confirmDelete(eId) {
    const confirmation = confirm('Are you sure you want to delete this user?');

    if (confirmation) {
        // If the user clicked "OK", proceed to delete the user
        window.location.href = `/delete?id=${eId}`;
    } else {
        // If the user clicked "Cancel", do nothing
        return false;
    }
}