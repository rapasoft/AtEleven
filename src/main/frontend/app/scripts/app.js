import React from "react";
import DailyMenuList from "./components/dailyMenuList";
import SelectedItems from "./components/selectedItems";
import {Sort, ORDER} from "./utils/sort";

const mountNode = document.getElementById("app");
let host = window.location.host;
if (host.includes('localhost')) {
  host = "localhost:8080/"
}
const HOST = 'http://' + host + '/at11/';

const getCookie = (cname) => {
  var name = cname + "=";
  var ca = document.cookie.split(';');
  for (var i = 0; i < ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == ' ') c = c.substring(1);
    if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
  }
  return "[]";
};

export default class DailyMenuApp extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      selected: [],
      data: []
    };
  }

  componentDidMount() {
    var component = this;
    var stateFromCookie = JSON.parse(getCookie("userPrefs"));
    this.serverRequest = $.ajax({
      url: HOST + 'daily-menu',
      type: 'GET',
      contentType: 'application/json',
      dataType: 'json',
      crossDomain: true,
      success: (result) => {
        component.setState({
          selected: (stateFromCookie.length > 0 ? stateFromCookie : result.map((e) => {
            return {name: e.restaurantName, selected: true}
          })),
          data: Sort.byContentSize(result, ORDER.DESC)
        });
      },
      error: (xhr, status, error) => console.log(xhr, status, error)
    })
  }

  componentWillUnmount() {
    this.serverRequest.abort();
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
    document.cookie = "userPrefs=" + JSON.stringify(selected);
    return selected;
  }

  render() {
    return (
      <div className="main">
        <nav className="navbar navbar-default navbar-fixed-top">
          <div className="row legend">
            <div className="col-sm-3">
              <h1>AtEleven</h1>
            </div>
            <div className="col-sm-6">
              <SelectedItems selected={this.state.selected} clickCallback={this.itemClicked.bind(this)}/>
            </div>
            <div className="col-sm-3">
              <h2>{new Date().toJSON().slice(0, 10)}</h2>
            </div>
          </div>
        </nav>
        <DailyMenuList data={this.state.data} selected={this.state.selected}/>
      </div>
    );
  }
}

React.render(<DailyMenuApp />, mountNode);
