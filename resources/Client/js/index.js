function pageLoad() {

    let now = new Date();

    let testHTML = '<div style="text-align:center;">'
        + '<h1>Welcome to my API powered website!</h1>'
        + '<img src="/client/img/quiz-logo"  alt="Logo"/>'
        + '<div style="font-style: italic;">'
        + 'Generated at ' + now.toLocaleTimeString()
        + '</div>'
        + '</div>';

    document.getElementById("testDiv").innerHTML = testHTML;

}