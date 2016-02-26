var React = window.React = require('react'),
  mountNode = document.getElementById("app"),
  HOST = 'http://' + window.location.host + '/at11/';

import DailyMenuList from './components/dailyMenuList'
import DailyMenuItem from './components/dailyMenuItem'

export default class DailyMenuApp extends React.Component {
  constructor(props) {
    super(props);
    this.state = {data: []}
  }

  componentDidMount() {
    this.serverRequest = $.get(HOST + 'daily-menu', function (result) {
      this.setState({
        data: result
      });
    }.bind(this));
  }

  componentWillUnmount() {
    this.serverRequest.abort();
  }

  render() {
    return (
      <div>
        <h1>AtEleven</h1>
        <h2>{new Date().toJSON().slice(0, 10)}</h2>
        <DailyMenuList data={this.state.data}/>
      </div>
    );
  }
}

React.render(<DailyMenuApp />, mountNode);
