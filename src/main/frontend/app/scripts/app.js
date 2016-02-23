var React = window.React = require('react'),
  mountNode = document.getElementById("app"),
  HOST = 'http://' + window.location.host + '/';

var DailyMenuApp = React.createClass({
  getInitialState: function () {
    return {
      data: []
    }
  },
  componentDidMount: function () {
    this.serverRequest = $.get(HOST + 'daily-menu', function (result) {
      this.setState({
        data: result
      });
    }.bind(this));
  },
  componentWillUnmount: function () {
    this.serverRequest.abort();
  },
  render: function () {
    return (
      <div>
        <h1>AtEleven</h1>
        <h2>{new Date().toJSON().slice(0, 10)}</h2>
        <DailyMenuList data={this.state.data}/>
      </div>
    );
  }
});

var DailyMenuList = React.createClass({
  render: function () {
    var listItems = this.props.data.map(function (item) {
      return (
        <DailyMenuItem key={item.restaurantName}
                       restaurantName={item.restaurantName}
                       date={item.date}
                       soups={item.soups}
                       mainDishes={item.mainDishes}
                       other={item.other}/>
      );
    });
    return (
      <div className="daily-menu-list">
        {listItems}
      </div>
    )
  }
});

var DailyMenuItem = React.createClass({
  render: function () {
    return (
      <div className="menu-item">
        <h3>{this.props.restaurantName}</h3>
        <h4>Soups</h4>
        <RenderList data={this.props.soups}/>
        <h4>Main Dishes</h4>
        <RenderList data={this.props.mainDishes}/>
        <h4>Other</h4>
        <RenderList data={this.props.other}/>
      </div>
    );
  }
});

var RenderList = React.createClass({
  render: function () {
    var listItems = 'N/A';
    if (this.props.data) {
      listItems = this.props.data.map(function (item) {
        return (
          <li key={item}>{item}</li>
        );
      });
    }
    if (listItems.length > 0) {
      return (
        <ul>{listItems}</ul>
      );
    } else {
      return <p>N/A</p>;
    }
  }
});

React.render(<DailyMenuApp />, mountNode);
