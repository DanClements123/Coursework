
function loadQuiz() {
    let QuestionsHTML = '<table>' +
        '<tr>' +
        `<td>test</td>` +
        '</tr>'
    fetch('/Questions/quizName', {method: 'get'}
    ).then(response => response.json()
    ).then(Questions => {

        for (let quizName of Questions) {
            QuestionsHTML += `<tr>` +
          `<td>${quizName.quizName}</td>` +
                `<td>${quizName.buttonSelection}</td>` +
                `</td>` +
                `</tr>`;
        }
        QuestionsHTML += '</table>';
        let i = 0
        while(i <= 10){
            Math.floor((Math.random() * 10) + 1);
            i++;
        }
        if(i == buttonSelection){

        }

        document.getElementById("listDiv").innerHTML = QuestionsHTML;

    })

}//end of function

function pageLoad(){
    loadQuiz();
}//end of function