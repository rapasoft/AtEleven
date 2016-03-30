import React from "react";
import DailyMenuList from "./components/dailyMenuList";
import {Sort, ORDER} from "./utils/sort";
import {exampleData} from "../test/example-data";

const mountNode = document.getElementById("app");
const HOST = 'http://' + window.location.host + '/at11/';

export default class DailyMenuApp extends React.Component {
  constructor(props) {
    super(props);
    this.state = {data: Sort.byContentSize(exampleData, ORDER.DESC)};
  }

  componentDidMount() {
    this.serverRequest = $.get(HOST + 'daily-menu', function (result) {
      this.setState({
        data: Sort.byContentSize(result, ORDER.DESC)
      });
    }.bind(this));
  }

  componentWillUnmount() {
    this.serverRequest.abort();
  }

  render() {
    return (
      <div className="main">
        <nav className="navbar navbar-default navbar-fixed-top">
          <h1>AtEleven</h1>
          <h2>{new Date().toJSON().slice(0, 10)}</h2>
        </nav>
        <DailyMenuList data={this.state.data}/>
      </div>
    );
  }
}

React.render(<DailyMenuApp />, mountNode);
