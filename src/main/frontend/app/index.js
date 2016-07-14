require('bootstrap-webpack!../bootstrap.config.js');
require('file?name=[name].[ext]!index.html');
require('./styles/main.less');
require('whatwg-fetch');

import React from 'react';
import ReactDOM from 'react-dom';
import DailyMenuList from './components/dailyMenuList';
import SelectedItems from './components/selectedItems';
import {Sort, ORDER} from './utils/sort';

const mountNode = document.getElementById('app');
let host = window.location.host;
if (host.includes('localhost')) {
  host = 'localhost:8080/'
}
const HOST = 'http://' + host + '/at11/';

const getCookie = (cname) => {
  var name = cname + '=';
  var ca = document.cookie.split(';');
  for (var i = 0; i < ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == ' ') c = c.substring(1);
    if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
  }
  return '[]';
};

export default class DailyMenuApp extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      selected: require('test/example-data').exampleSelected,
      data: require('test/example-data').exampleData
    };
  }

  componentDidMount() {
    var component = this;
    var stateFromCookie = JSON.parse(getCookie('userPrefs'));

    fetch(HOST + 'daily-menu')
      .then((result) => result.json())
      .then((result) => {
        component.setState({
          selected: (stateFromCookie.length > 0 ? stateFromCookie : result.map((e) => {
            return {name: e.restaurantName, selected: true}
          })),
          data: Sort.byContentSize(result, ORDER.DESC)
        });
      });
  }

  itemClicked(event) {
    this.setState({
      selected: this.updateState(this, event)
    });
  }

  updateState(component, event) {
    let selected = component.state.selected.map((item) => {
      if (item.name === event.target.value) {
        item.selected = event.target.checked;
      }
      return item;
    });
    document.cookie = 'userPrefs=' + JSON.stringify(selected);
    return selected;
  }

  render() {
    return (
      <div className='main'>
        <nav className='navbar navbar-default navbar-fixed-top'>
          <button className='mobile-menu-switch' onClick={() => {
            let toBeHidden = document.getElementsByClassName('toBeHidden');

            toBeHidden.forEach(el => {
              if (el.style.display === 'none') {
                el.style.display = '';
              } else {
                el.style.display = 'none';
              }
            })
          }}>
            M
          </button>
          <div className='row legend'>
            <div className='col-sm-3'>
              <h1>AtEleven</h1>
            </div>
            <div className='col-sm-6 toBeHidden'>
              <SelectedItems selected={this.state.selected} clickCallback={this.itemClicked.bind(this)}/>
            </div>
            <div className='col-sm-3 toBeHidden'>
              <h2>{new Date().toJSON().slice(0, 10)}</h2>
            </div>
          </div>
        </nav>
        <DailyMenuList data={this.state.data} selected={this.state.selected}/>
      </div>
    );
  }
}

ReactDOM.render(<DailyMenuApp />, mountNode);
