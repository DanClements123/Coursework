function logos() {
    let testHTML = '<div style="text-align:right; position:fixed;" >'
        + '<img src="/client/img/quiz-logo.jpg" style="height:105px" alt="Logo"/>';
    document.getElementById("testDiv").innerHTML = testHTML;
}

/*
+ '<img src="/client/img/Cartoon%20Computer.jpg" style="height:105px" alt="Logo2"/>';
+ '<img src="/client/img/english.jpg" style="height:105px" alt="Logo3"/>';
+ '<img src="/client/img/History%20img.png" style="height:105px" alt="Logo4"/>';
+ '<img src="/client/img/Politics.jpg" style="height:105px" alt="Logo5"/>';
+ '<img src="/client/img/Science.png" style="height:105px" alt="Logo6"/>';
*/

function checkLogin() {

    let username = Cookies.get("username");

    let logInHTML = '';

    if (username === undefined) {

        let editButtons = document.getElementsByClassName("editButton");
        for (let button of editButtons) {
            button.style.visibility = "hidden";
        }

        let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            button.style.visibility = "hidden";
        }

        logInHTML = "Not logged in. <a href='/client/login.html'>Log in</a>";

    } else {

        let editButtons = document.getElementsByClassName("editButton");
        for (let button of editButtons) {
            button.style.visibility = "visible";
        }

        let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            button.style.visibility = "visible";
        }


        logInHTML = "Logged in as " + username + ". <a href='/client/login.html?logout'>Log out</a>";

    }

    document.getElementById("loggedInDetails").innerHTML = logInHTML;

}

checkLogin();