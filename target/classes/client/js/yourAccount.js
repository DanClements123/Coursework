function loadName() {
    fetch('/userInfo/list', {method: 'get'}
    ).then(response => response.json()
    ).then(data => {
            if (data.hasOwnProperty('error')) {

                alert(data.error);

            } else {

            let yourAccountHTML = `<div class="container">`;
                + `<div class="col-6 bg-light font-weight-bold">Username</div>`;

                yourAccountHTML += `</div>`;
                document.getElementById('userName').innerHTML = yourAccountHTML;
            }
    })
}
