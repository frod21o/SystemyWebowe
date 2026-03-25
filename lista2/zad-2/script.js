const form = document.getElementById("packageForm");
const tableBody = document.getElementById("tableBody");
const totalVolumeEl = document.getElementById("totalVolume");
const searchInput = document.getElementById("search");

let totalVolume = 0;

// (c) walidacja + submit
form.addEventListener("submit", function (e) {
    e.preventDefault();

    if (!form.checkValidity()) {
        form.classList.add("was-validated");
        return;
    }

    const name = document.getElementById("name").value;
    const w = parseInt(document.getElementById("width").value);
    const h = parseInt(document.getElementById("height").value);
    const d = parseInt(document.getElementById("depth").value);

    // (e) objętość w m^3
    let volume = (w * h * d) / 1000000;
    volume = volume.toFixed(2);

    totalVolume += parseFloat(volume);

    // dodanie do tabeli
    const row = document.createElement("tr");

    row.innerHTML = `
<td>${name}</td>
<td>${w}</td>
<td>${h}</td>
<td>${d}</td>
<td>${volume}</td>
`;

    tableBody.appendChild(row);

    // aktualizacja sumy
    totalVolumeEl.textContent = totalVolume.toFixed(2);

    form.reset();
    form.classList.remove("was-validated");
});

// (g) filtrowanie
searchInput.addEventListener("input", function () {
    const filter = this.value.toLowerCase();
    const rows = tableBody.getElementsByTagName("tr");

    for (let row of rows) {
        const text = row.textContent.toLowerCase();
        row.style.display = text.includes(filter) ? "" : "none";
    }
});