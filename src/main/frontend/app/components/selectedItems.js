import React from 'react';

export default class SelectedItems extends React.Component {

  render() {
    return <form className='selected-restaurants'>
      {
        this.props.selected.map((e, i) => {
          return <label key={i}>
            <input type='checkbox' defaultChecked={e.selected} value={e.name} onClick={this.props.clickCallback}/>
            {e.name}
          </label>
        })
      }
    </form>;
  }

}

SelectedItems.propTypes = {
  selected: React.PropTypes.array,
  clickCallback: React.PropTypes.func
};
