import React, {useState, useEffect, useCallback} from "react"
import './App.css';
import axios from "axios";
import {useDropzone} from 'react-dropzone'

const UserProfiles = () => {
        const [userProfile, setUserProfiles] = useState([]);

        const fetchUserProfiles = () => {
            axios.get("http://localhost:8080/gcp/user-profile").then(res => {
                console.log(res);
                setUserProfiles(res.data);
            });
        };

        useEffect(() => {
            fetchUserProfiles();
        }, []);
        return (
            <div className="user-profile">
                {userProfile.map((userProfile, index) => {
                    return (
                        <div className="user-profile-item" key={index}>
                            {userProfile.userProfileID ? (
                                <img
                                    src={`http://localhost:8080/gcp/user-profile/${userProfile.userProfileID}/image/download`}
                                    alt={userProfile.userProfileID}
                                />
                            ) : null}
                            <br/>
                            <h1>{userProfile.username}</h1>
                            <Dropzone{...userProfile} />
                            <br/>
                        </div>
                    );
                })}
            </div>
        );
    };

function Dropzone(userProfile) {
    const onDrop = useCallback(acceptedFiles => {
        const file = acceptedFiles[0];

        console.log(file);

        const formData = new FormData();
        formData.append("file", file);
        axios.post(`http://localhost:8080/gcp/user-profile/${userProfile.userProfileID}/image/upload`,
            formData,
            {
                headers: {
                "Content-Type" : "multipart/form-data"
                }
            }
            ).then(() => {
                console.log("File Uploaded Successfully")
            } )
            .catch(err => {
            console.log(err);
            });

    }, [])
    const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

    return (
        <div {...getRootProps()}>
            <input {...getInputProps()} />
            {
                isDragActive ?
                    <p>Drop the image here ...</p> :
                    <p>Drag 'n' drop image here, or click to select image</p>
            }
        </div>
    )
}

function App() {
  return(
      <div className="App">
        <UserProfiles/>
      </div>
  );
}
export default App;