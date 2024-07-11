import React, { useState } from 'react';
import '../styles/components/TagIcon.css'; // Import the CSS for styling

const TagIcon = ({tags, tagHandler}) => {
  const [showTags, setShowTags] = useState(false);


  return (
    <div className="tag-icon-container">
      <i
        className="fas fa-tags"
        onClick={() => setShowTags(!showTags)}
      ></i>
      {showTags && (
        <ul className="tag-list">
          {tags.map((tag) => (
            <li onClick={()=>{tagHandler(tag); setShowTags(false);}} key={tag.id}>{tag.name}</li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default TagIcon;
