// Stripe公開APIキーを使用します
const stripe = Stripe('pk_test_51QJ65DAcYK9h7ZJRbd5YvEWgseZtDlB7dVy3l9WRlbGTJcRGi7vhbwrWI00Bc7hpo39TQTiEpQGmdHyd6eCs61ff00GHY1vNA3');
const elements = stripe.elements();

const style = {
  base: {
    color: "#32325d",
    fontFamily: '"Helvetica Neue", Helvetica, sans-serif',
    fontSmoothing: "antialiased",
    fontSize: "16px",
    "::placeholder": {
      color: "#aab7c4"
    }
  },
  invalid: {
    color: "#fa755a",
    iconColor: "#fa755a"
  }
};

const card = elements.create("card", { style: style });
card.mount("#card-element");

card.on('change', ({error}) => {
  const displayError = document.getElementById('card-errors');
  if (error) {
    displayError.textContent = error.message;
  } else {
    displayError.textContent = '';
  }
});

const form = document.getElementById('payment-form');
form.addEventListener('submit', async (event) => {
  event.preventDefault();
  
  // カード名義人の名前を取得
  const cardholderName = document.getElementById('cardholder-name').value;

  const {error, paymentMethod} = await stripe.createPaymentMethod({
    type: 'card',
    card: card,
  });

  if (error) {
    // エラーメッセージを表示
    const errorElement = document.getElementById('card-errors');
    errorElement.textContent = error.message;
  } else {
    // サーバーにペイメントメソッドIDを送信
    const response = await fetch('/create-stripe-customer', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
		  paymentMethodId: paymentMethod.id,
		  cardholderName: cardholderName 
		  })
	  });

    if (response.ok) {
      // サーバー側の処理が成功した場合、リダイレクトを行う
      window.location.href = "/?updatedpaid";
    } else {
      // エラーの場合、エラーメッセージを表示
      const errorElement = document.getElementById('card-errors');
      errorElement.textContent = 'クレジットカード情報の更新に失敗しました。';
    }
  }
});
