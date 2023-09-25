// Get dropdown elements
const illerDropdown = document.getElementById("illerDropdown");
const ilcelerDropdown = document.getElementById("ilcelerDropdown");
const mahallelerDropdown = document.getElementById("mahallelerDropdown");
const sokaklarDropdown = document.getElementById("sokaklarDropdown");

// Populate the Iller dropdown initially
fetch("http://localhost:8093/address/iller")
    .then(response => response.json())
    .then(data => {
        console.log(data)
        data.forEach(item => {
            const option = new Option(item.ilAdi, item.id);
            illerDropdown.add(option);
        });
    });

// When Iller is selected, populate Ilceler
illerDropdown.addEventListener("change", function () {
    const selectedId = this.value;
    fetch("http://localhost:8093/address/iller/" + selectedId + "/ilceler")
        .then(response => response.json())
        .then(data => {
            console.log("il id" + selectedId + "\n")
            console.log(data)
            ilcelerDropdown.innerHTML = "<option>Ilce seciniz..</option>";
            data.forEach(item => {
                const option = new Option(item.ilceAdi, item.id);
                ilcelerDropdown.add(option);
            });
        });
});

// When Ilceler is selected, populate mahalleler
ilcelerDropdown.addEventListener("change", function () {
    const selectedId = this.value;
    fetch("http://localhost:8093/address/ilceler/" + selectedId + "/mahalleler")
        .then(response => response.json())
        .then(data => {
            console.log("ilce id" + selectedId + "\n")
            console.log(data)
            mahallelerDropdown.innerHTML = "<option>Mahalle seciniz..</option>";
            data.forEach(item => {
                const option = new Option(item.mahalleAdi, item.id);
                mahallelerDropdown.add(option);
            });
        });
});

// When mahalleler is selected, populate sokaklar
mahallelerDropdown.addEventListener("change", function () {
    const selectedId = this.value;
    fetch("http://localhost:8093/address/mahalleler/" + selectedId + "/sokaklar")
        .then(response => response.json())
        .then(data => {
            console.log("mahalle id " + selectedId + "\n")
            console.log(data)
            sokaklarDropdown.innerHTML = "<option>Sokak seciniz..</option>";
            data.forEach(item => {
                const option = new Option(item.sokakAdi, item.id);
                sokaklarDropdown.add(option);
            });
        });
});
