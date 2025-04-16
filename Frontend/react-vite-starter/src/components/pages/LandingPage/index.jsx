import styles from './index.module.css';
import logo from '../../../assets/ChatGPT.png'
import { Link } from 'react-router';
import { useState, useEffect } from 'react';

function LandingPage() {
  const [models, setModels] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/models")
      .then(response => response.json())
      .then(modelsData => setModels(modelsData));
  }, [])

  return (
    <div className={styles.container}>
      <img src={logo} className={styles.logo} />

      <div className={styles.listContainer}>
        <div className={styles.listHeader}>
          Choose your model
        </div>
        <ul className={styles.list}>
          {models && models.length > 0 ? models.map((model, index) =>
            <li key={index}>
              <Link to={`/chat/${model}`} className={styles.modelLink}>
                {model}
              </Link>
            </li>
          ) : <p className={styles.modelLink}>No models are currently available</p>
          }
        </ul>
      </div>
    </div>
  )
}

export default LandingPage;
