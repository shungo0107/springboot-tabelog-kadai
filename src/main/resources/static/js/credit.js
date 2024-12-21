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


// ================カスタマーID登録・更新================

const form = document.getElementById('payment-form');
form.addEventListener('submit', async (event) => {
  event.preventDefault();
  
  
  // カード名義人の名前を取得
  const cardholderName = document.getElementById('cardholder-name').value;
  const errorMessage = document.getElementById('cardholdername-errors');

if (!cardholderName.trim()) {
    errorMessage.textContent = "カード名義人を入力してください";
    return;
}
if (!/^[A-Z]+$/.test(cardholderName)) {
    errorMessage.textContent = "アルファベット(半角、大文字)のみで入力してください";
    return;
}
 // チェックが通った場合の処理
errorMessage.textContent = "";

  // カスタマーIDを取得（更新用）
  const customerId = document.getElementById('customer-id').value.trim();
  
  console.log("cardholderName：" + cardholderName);
  console.log("customerId：" + customerId);

  const {error, paymentMethod} = await stripe.createPaymentMethod({
    type: 'card',
    card: card,
  });

  if (error) {
    // エラーメッセージを表示
    const errorElement = document.getElementById('card-errors');
    errorElement.textContent = error.message;
  } else {
	  
	  let response;
	  
	  if(customerId && customerId != null && customerId.length != 0){
		  console.log("=======更新開始=======");
		  console.log("customerId：" + customerId);
		  		  
		     // ここで顧客IDも一緒に送信する
		     response = await fetch('/update-payment-method', {
				 method: 'POST',
				 headers: {
					  'Content-Type': 'application/json'
				  },
				  body: JSON.stringify({
					  paymentMethodId: paymentMethod.id,
					  cardholderName: cardholderName,
					  customerId: customerId
				  })
			  })
	  } else {
		  console.log("=======登録開始=======");
		  console.log("customerId：" + customerId);

		      // サーバーにペイメントメソッドIDを送信
		      response = await fetch('/create-stripe-customer', {
				   method: 'POST',
				   headers: {
					   'Content-Type': 'application/json'
					   },
					   body: JSON.stringify({
						   paymentMethodId: paymentMethod.id,
						   cardholderName: cardholderName 
						   })
			   })
	   }
;

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


// ================カスタマーID削除================

 // Stripeとの通信開始
 const deleteBtn = document.getElementById('delete-customer')
 deleteBtn.addEventListener('click', async (event) => {
    event.preventDefault();

    const customerId = document.getElementById('customer-id').value;
    console.log("customerId：" + customerId);
    console.log("customerId：" + customerId);
    
    let response;
    
    if(customerId && customerId != null && customerId.length != 0){
		console.log("======カスタマーID削除開始======");
		
	    // サーバーに顧客IDを送信
	    response = await fetch('/delete-customer', {
	        method: 'POST',
	        headers: { 'Content-Type': 'application/json' },
	        body: JSON.stringify({ customerId: customerId }),
	    });		
	} else {
		console.log("======DB更新開始======");
		response = await fetch('/user/delete', {
			method: 'GET',
			headers: { 'Content-Type': 'application/json' }
        }); 
	}

    if (response.ok) {
        window.location.href = "/?deletedpaid";
    } else {
        alert('顧客の削除に失敗しました。');
    }
});


