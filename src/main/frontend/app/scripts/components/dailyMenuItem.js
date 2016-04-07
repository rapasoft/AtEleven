import React from "react";

var RenderList = React.createClass({
    render: function () {
      var listItems = 'N/A';
      if (this.props.data) {
        listItems = this.props.data.map((item, i) => {
            return (
              <li key={i}>
                {item.foodType.map((type, j) => <span key={j} style={ JSON.parse(type.style) } className="category"
                                                      title={type.type}>{type.type[0].toUpperCase()}</span>)}
                {item.description}
              </li>
            );
          }
        );
      }
      if (listItems.length > 0) {
        return (
          <ul>{listItems}</ul>
        );
      } else {
        return <p>N/A</p>;
      }
    }
  })
  ;


export default class DailyMenuItem extends React.Component {
  render() {
    if (this.props.rendered) {
    return (
      <div className="menu-item panel panel-default">
        <div className="panel-heading">{this.props.restaurantName}</div>
        <div className="panel-body">
          <h4>Soups</h4>
          <RenderList data={this.props.soups}/>
          <h4>Main Dishes</h4>
          <RenderList data={this.props.mainDishes}/>
          {this.props.other.length > 0 && <h4>Other</h4> }
          {this.props.other.length > 0 && <RenderList data={this.props.other}/>}
        </div>
      </div>
    )
    } else {
      return null;
    }
  }
}
