function loadQuiz() {

    let QuestionsHTML = `<table>` +
        `<tr>` +
        `</tr>`;

    fetch('/Questions/quizName', {method: 'get'}
    ).then(response => response.json()
    ).then(Questions => {
            for (let question of Questions) {
            let i = 0;
            let random = 0;
            while (i <= 10) {
                random = Math.floor((Math.random() * Questions.size) + 1);
                i++;
            }
            QuestionsHTML += `<tr>` +
                `<td>${question.quizName}</td>` +
                `<td><button class="playButton" data-id='${question.id}'>Play Quiz</button><td>` +
                `</tr>`;
        }

        QuestionsHTML += `</table>`;
        document.getElementById("listDiv").innerHTML = QuestionsHTML;
        let editButtons = document.getElementsByClassName("playButton");
        for (let button of editButtons) {
            button.addEventListener("click", playButton);
        }
    })

}//end of function

function pageLoad(){
    loadQuiz();

}//end of function