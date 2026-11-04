package view;

public class FoodCalView {

    public String getHtml() {

        return """
<!DOCTYPE html>
<html lang="id">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>FoodCal</title>

<style>
* {
    box-sizing: border-box;
}

body {
    margin: 0;
    font-family: Arial, sans-serif;
    background: #f3f8f4;
    color: #26352b;
}

.hidden {
    display: none !important;
}

.login-page {
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 20px;
}

.login-card {
    width: 100%;
    max-width: 430px;
    background: white;
    padding: 32px;
    border-radius: 18px;
    box-shadow: 0 8px 30px #00000012;
}

.logo {
    text-align: center;
    color: #218653;
    font-size: 32px;
    font-weight: bold;
}

.subtitle {
    text-align: center;
    color: #68766b;
}

label {
    display: block;
    margin: 12px 0 6px;
}

input, select {
    width: 100%;
    padding: 11px;
    margin: 4px 0 12px;
    border: 1px solid #ccd8ce;
    border-radius: 8px;
}

button {
    border: 0;
    border-radius: 8px;
    padding: 11px 15px;
    cursor: pointer;
    font-weight: bold;
}

.primary {
    background: #218653;
    color: white;
}

.wide {
    width: 100%;
    margin-top: 8px;
}

.linkbtn {
    background: transparent;
    color: #218653;
}

.error {
    color: #b42318;
    text-align: center;
    margin-top: 12px;
}

.success {
    color: #187344;
    text-align: center;
    margin-top: 12px;
}

header {
    background: #218653;
    color: white;
    padding: 22px 7%;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

header h1 {
    margin: 0 0 8px;
}

header p {
    margin: 0;
}

.logout {
    background: white;
    color: #218653;
}

.container {
    max-width: 1050px;
    margin: 30px auto;
    padding: 0 18px;
}

.summary {
    background: #218653;
    color: white;
    padding: 24px;
    border-radius: 14px;
    margin-bottom: 22px;
}

.summary strong {
    font-size: 30px;
}

.grid {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 20px;
}

.card {
    background: white;
    padding: 22px;
    border-radius: 14px;
    margin-bottom: 20px;
}

table {
    width: 100%;
    border-collapse: collapse;
}

th, td {
    text-align: left;
    padding: 11px 7px;
    border-bottom: 1px solid #e5ece6;
}

.delete {
    background: #ffe8e6;
    color: #b42318;
}

.result {
    margin-top: 14px;
    padding: 14px;
    background: #e7f6eb;
    border-radius: 8px;
}

@media(max-width:650px) {
    .grid {
        grid-template-columns: 1fr;
    }
}
               
/* TABEL DAFTAR MAKANAN */
              
              .table-container {
                  max-height: 270px;
                  overflow-y: auto;
                  overflow-x: auto;
                  border: 1px solid #e5ece6;
                  border-radius: 8px;
              }
              
              .table-container table {
                  width: 100%;
                  border-collapse: collapse;
              }
              
              .table-container thead th {
                  position: sticky;
                  top: 0;
                  background: #e7f6eb;
                  z-index: 2;
              }
              
              .table-container th,
              .table-container td {
                  padding: 10px;
                  border-bottom: 1px solid #e5ece6;
                  white-space: nowrap;
              }
              
              .table-container tbody tr:hover {
                  background: #f3f8f4;
              }              
               
</style>
</head>

<body>

<!-- HALAMAN LOGIN DAN REGISTRASI -->

<section id="authPage" class="login-page">

<div class="login-card">

    <div class="logo">🍏 FoodCal</div>

    <p class="subtitle" id="authSubtitle">
        Login untuk menggunakan FoodCal
    </p>

    <!-- FORM LOGIN -->

    <form id="loginForm">

        <label>Username</label>

        <input id="loginNama"
               required
               placeholder="Masukkan username">

        <label>Password</label>

        <input id="loginPassword"
               type="password"
               required
               placeholder="Masukkan password">

        <button class="primary wide" type="submit">
            Login
        </button>

    </form>

    <!-- FORM REGISTRASI -->

    <form id="registerForm" class="hidden">

        <label>Username</label>

        <input id="regNama"
               required
               minlength="3"
               placeholder="Buat username">

        <label>Password</label>

        <input id="regPassword"
               type="password"
               required
               minlength="6"
               placeholder="Buat password">

        <label>Ulangi Password</label>

        <input id="regUlang"
               type="password"
               required
               minlength="6"
               placeholder="Ulangi password">

        <button class="primary wide" type="submit">
            Daftar
        </button>

    </form>

    <div id="authMessage" class="error"></div>

    <p style="text-align:center" id="switchText">
        Belum punya akun?
        <button class="linkbtn"
                id="showRegister"
                type="button">
            Registrasi
        </button>
    </p>

</div>

</section>


<!-- HALAMAN UTAMA -->

<section id="appPage" class="hidden">

<header>

    <div>
        <h1>🍏 FoodCal</h1>
        <p>Penghitung Kalori Makanan</p>
    </div>

    <button class="logout" id="logoutBtn">
        Logout
    </button>

</header>


<main class="container">

    <!-- TOTAL KALORI -->

    <section class="summary">

        <h2>Total Kalori</h2>

        <strong id="total">0 kalori</strong>

        <p>
            Total kalori dari seluruh makanan yang dipilih.
        </p>

    </section>


    <div class="grid">

        <!-- TAMBAH MAKANAN KE DATABASE -->

        <section class="card">

            <h3>Tambah Makanan</h3>

            <label>Nama makanan</label>

            <input id="newNama"
                   placeholder="Contoh: Nasi Goreng">

            <label>Kalori per porsi</label>

            <input id="newKalori"
                   type="number"
                   min="0.01"
                   step="any"
                   placeholder="Contoh: 250">

            <button class="primary" id="addFoodBtn">
                + Simpan Makanan
            </button>

        </section>


        <!-- PILIH MAKANAN -->

        <section class="card">

            <h3>Hitung Kalori Makanan</h3>

            <label>Pilih makanan</label>

            <select id="foodSelect"></select>

            <label>Jumlah porsi</label>

            <input id="portion"
                   type="number"
                   min="0.1"
                   step="0.1"
                   value="1">

            <button class="primary" id="addCartBtn">
                Tambahkan Makanan
            </button>

            <p>
                Kamu bisa menambahkan beberapa jenis makanan
                sekaligus.
            </p>

        </section>

    </div>


    <!-- DAFTAR PERHITUNGAN -->

    <section class="card">

        <h3>Daftar Perhitungan Makanan</h3>

        <table>

            <thead>
                <tr>
                    <th>Makanan</th>
                    <th>Kalori/Porsi</th>
                    <th>Porsi</th>
                    <th>Subtotal</th>
                    <th>Aksi</th>
                </tr>
            </thead>

            <tbody id="cartBody"></tbody>

        </table>

        <button class="primary"
                id="resetCartBtn">
            Kosongkan Perhitungan
        </button>

        <div class="result" id="cartResult">
            Pilih makanan untuk menghitung kalori.
        </div>

    </section>


    <!-- DATA MAKANAN DARI DATABASE -->

    <section class="card">

        <h3>Daftar Makanan</h3>

        <table>

            <thead>
                <tr>
                    <th>Makanan</th>
                    <th>Kalori/Porsi</th>
                    <th>Aksi</th>
                </tr>
            </thead>

            <tbody id="foodBody"></tbody>

        </table>

    </section>

</main>

</section>


<script>

let token = sessionStorage.getItem("foodcal_token") || "";

let foods = [];
let cart = [];


// FUNGSI API

async function api(url, options = {}) {

    options.headers = Object.assign(
        {},
        options.headers || {}
    );

    if (token) {
        options.headers["Authorization"] =
            "Bearer " + token;
    }

    const response = await fetch(url, options);

    let data = {};

    try {
        data = await response.json();
    } catch (e) {}

    if (!response.ok) {
        throw new Error(
            data.message || "Permintaan gagal."
        );
    }

    return data;
}


function formBody(values) {
    return new URLSearchParams(values).toString();
}


function showMessage(text, success = false) {

    const el = document.getElementById("authMessage");

    el.textContent = text;

    el.className = success ? "success" : "error";
}


// PINDAH KE REGISTRASI

function showRegister() {

    document.getElementById("loginForm")
        .classList.add("hidden");

    document.getElementById("registerForm")
        .classList.remove("hidden");

    document.getElementById("authSubtitle")
        .textContent = "Buat akun FoodCal baru.";

    document.getElementById("switchText").innerHTML =
        'Sudah punya akun? <button class="linkbtn" id="showLogin" type="button">Login</button>';

    document.getElementById("showLogin").onclick =
        showLogin;

    showMessage("");
}


// PINDAH KE LOGIN

function showLogin() {

    document.getElementById("registerForm")
        .classList.add("hidden");

    document.getElementById("loginForm")
        .classList.remove("hidden");

    document.getElementById("authSubtitle")
        .textContent = "Login untuk menggunakan FoodCal.";

    document.getElementById("switchText").innerHTML =
        'Belum punya akun? <button class="linkbtn" id="showRegister" type="button">Registrasi</button>';

    document.getElementById("showRegister").onclick =
        showRegister;

    showMessage("");
}


document.getElementById("showRegister").onclick =
    showRegister;


// PROSES REGISTRASI

document.getElementById("registerForm")
.addEventListener("submit", async function(e) {

    e.preventDefault();

    const nama =
        document.getElementById("regNama").value.trim();

    const password =
        document.getElementById("regPassword").value;

    const ulang =
        document.getElementById("regUlang").value;

    if (password !== ulang) {

        showMessage("Password tidak sama.");

        return;
    }

    try {

        const response = await fetch(
            "/api/register",
            {
                method: "POST",

                headers: {
                    "Content-Type":
                    "application/x-www-form-urlencoded"
                },

                body: formBody({
                    nama: nama,
                    password: password
                })
            }
        );

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message);
        }

        showLogin();

        document.getElementById("loginNama").value =
            nama;

        showMessage(
            "Registrasi berhasil. Silakan login.",
            true
        );

    } catch (error) {

        showMessage(error.message);
    }

});


// PROSES LOGIN

document.getElementById("loginForm")
.addEventListener("submit", async function(e) {

    e.preventDefault();

    const nama =
        document.getElementById("loginNama").value.trim();

    const password =
        document.getElementById("loginPassword").value;

    try {

        const response = await fetch(
            "/api/login",
            {
                method: "POST",

                headers: {
                    "Content-Type":
                    "application/x-www-form-urlencoded"
                },

                body: formBody({
                    nama: nama,
                    password: password
                })
            }
        );

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message);
        }

        token = data.token;

        sessionStorage.setItem(
            "foodcal_token", token
        );

        await openApp();

    } catch (error) {

        showMessage(error.message);
    }

});


// MEMBUKA HALAMAN UTAMA

async function openApp() {

    document.getElementById("authPage")
        .classList.add("hidden");

    document.getElementById("appPage")
        .classList.remove("hidden");

    try {

        await loadFoods();

    } catch (error) {

        alert(error.message);

        logout();
    }
}


// MENGAMBIL DATA MAKANAN DARI MYSQL

async function loadFoods() {

    foods = await api("/api/makanan");

    renderFoods();
}


// MENAMPILKAN MAKANAN

function renderFoods() {

    const body =
        document.getElementById("foodBody");

    const select =
        document.getElementById("foodSelect");

    body.replaceChildren();

    select.replaceChildren();

    foods.forEach(function(food) {

        const tr = document.createElement("tr");

        const nama = document.createElement("td");
        nama.textContent = food.nama;

        const kalori = document.createElement("td");
        kalori.textContent = food.kalori;

        const action = document.createElement("td");

        const btn = document.createElement("button");

        btn.textContent = "Hapus";
        btn.className = "delete";

        btn.onclick = function() {
            deleteFood(food.id);
        };

        action.appendChild(btn);

        tr.append(nama, kalori, action);

        body.appendChild(tr);


        const option = document.createElement("option");

        option.value = food.id;

        option.textContent =
            food.nama + " (" + food.kalori + " kalori)";

        select.appendChild(option);

    });

}


// MENAMBAHKAN MAKANAN KE DATABASE

document.getElementById("addFoodBtn").onclick =
async function() {

    const nama =
        document.getElementById("newNama").value.trim();

    const kalori =
        document.getElementById("newKalori").value;

    if (!nama || !kalori || Number(kalori) <= 0) {

        alert("Isi nama dan kalori dengan benar.");

        return;
    }

    try {

        await api(
            "/api/makanan",
            {
                method: "POST",

                headers: {
                    "Content-Type":
                    "application/x-www-form-urlencoded"
                },

                body: formBody({
                    nama: nama,
                    kalori: kalori
                })
            }
        );

        document.getElementById("newNama").value = "";

        document.getElementById("newKalori").value = "";

        await loadFoods();

        alert("Makanan berhasil ditambahkan.");

    } catch (error) {

        alert(error.message);
    }

};


// MENGHAPUS MAKANAN DARI DATABASE

async function deleteFood(id) {

    if (!confirm("Hapus makanan ini?")) {
        return;
    }

    try {

        await api(
            "/api/makanan?id=" + id,
            {
                method: "DELETE"
            }
        );

        cart = cart.filter(
            item => item.id !== id
        );

        renderCart();

        await loadFoods();

    } catch (error) {

        alert(error.message);
    }
}


// MENAMBAHKAN BEBERAPA MAKANAN KE PERHITUNGAN

document.getElementById("addCartBtn").onclick =
function() {

    const id = Number(
        document.getElementById("foodSelect").value
    );

    const porsi = Number(
        document.getElementById("portion").value
    );

    const food = foods.find(
        item => item.id === id
    );

    if (!food) {

        alert("Pilih makanan terlebih dahulu.");

        return;
    }

    if (!Number.isFinite(porsi) || porsi <= 0) {

        alert("Jumlah porsi harus lebih dari 0.");

        return;
    }

    cart.push({

        id: food.id,

        nama: food.nama,

        kalori: Number(food.kalori),

        porsi: porsi

    });

    renderCart();
};


// MENGHITUNG TOTAL KALORI

function renderCart() {

    const body =
        document.getElementById("cartBody");

    body.replaceChildren();

    let total = 0;

    cart.forEach(function(item, index) {

        const subtotal =
            item.kalori * item.porsi;

        total += subtotal;

        const tr = document.createElement("tr");

        const values = [
            item.nama,
            item.kalori,
            item.porsi,
            subtotal
        ];

        values.forEach(function(value) {

            const td = document.createElement("td");

            td.textContent = Number(value)
                .toLocaleString("id-ID");

            tr.appendChild(td);

        });

        const action = document.createElement("td");

        const btn = document.createElement("button");

        btn.textContent = "Hapus";

        btn.className = "delete";

        btn.onclick = function() {

            cart.splice(index, 1);

            renderCart();
        };

        action.appendChild(btn);

        tr.appendChild(action);

        body.appendChild(tr);

    });


    document.getElementById("total").textContent =
        total.toLocaleString("id-ID", {
            maximumFractionDigits: 2
        }) + " kalori";


    document.getElementById("cartResult").textContent =
        cart.length
        ? "Total dari " + cart.length +
          " pilihan makanan: " +
          total.toLocaleString("id-ID") +
          " kalori."
        : "Pilih makanan untuk menghitung kalori.";

}


// MENGOSONGKAN PERHITUNGAN

document.getElementById("resetCartBtn").onclick =
function() {

    cart = [];

    renderCart();
};


// LOGOUT

document.getElementById("logoutBtn").onclick =
function() {

    token = "";

    sessionStorage.removeItem("foodcal_token");

    cart = [];

    document.getElementById("appPage")
        .classList.add("hidden");

    document.getElementById("authPage")
        .classList.remove("hidden");

    document.getElementById("loginPassword").value = "";

    showLogin();
};


// MEMERIKSA SESI

if (token) {
    openApp();
}

</script>

</body>
</html>
""";
    }
}