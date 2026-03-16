
import './App.css';
import Footer from './shared/Footer';
import Headers from './shared/Header';
import Routing from './Routing';
import { Provider } from 'react-redux';
import store from './reduxContainer/Store';

function App() {
  return (
    <div className="App">
      <Provider store={store}>
      <Headers></Headers>
      <hr></hr>
      <Routing></Routing>
      <Footer></Footer>
      </Provider>
    </div>
  );
}

export default App;
