import { useParams } from 'react-router';
import logo from '../../../assets/ChatGPT.png'
import styles from './index.module.css';
import { useEffect, useRef, useState } from 'react';

function ChatPage() {
  const { model } = useParams();

  const [inputMessage, setInputMessage] = useState('');
  const [messages, setMessages] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  const scrollToEndRef = useRef(null);

  useEffect(() => {
    fetch("http://localhost:8080/messages")
      .then(response => response.json())
      .then(messagesData => setMessages(messagesData));
  }, []);

  useEffect(() => {
    scrollToEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages])

  const handleInputMessage = (message) => {
    setInputMessage(message.target.value);
  }

  const handleFormMessage = (event) => {
    event.preventDefault();

    const message = { role: 'user', content: inputMessage };
    setMessages(messages => [...messages, message]);

    setInputMessage('');
    setIsLoading(true);

    fetch("http://localhost:8080/messages", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        model: model,
        ...message
      })
    })
      .then(response => response.json())
      .then(responseMessage => {
        setIsLoading(false);
        setMessages(messages => [...messages, responseMessage])
      });
  }

  return (
    <div className={styles.container}>
      <div className={styles.headerContainer}>
        <img src={logo} alt="App logo" className={styles.logoContainer} />
      </div>

      <div className={styles.messagesContainer}>
        {messages && messages.length > 0 && messages.map((message, index) =>
          <div
            key={index}
            className={`${styles.message} ${message.role === 'user' ? styles.userMessage : styles.modelMessage}`}>
            {message.content}
          </div>
        )}
        {isLoading && <div
          className={`${styles.message} ${styles.modelMessage}`}>
          Thinking...
        </div>}
        <div ref={scrollToEndRef}></div>
      </div>

      <div className={styles.inputContainer}>
        <form onSubmit={handleFormMessage} className={styles.inputForm}>
          <textarea
            value={inputMessage}
            onChange={handleInputMessage}
            className={styles.textContainer}
            placeholder="Please write your message here..."
            rows={5}
          />
          <button className={styles.sendButton}>Ask</button>
        </form>
      </div>
    </div>
  )
}

export default ChatPage;
