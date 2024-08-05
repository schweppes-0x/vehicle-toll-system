import React from 'react';

interface VehicleInfoProps {
    vehicleInfo: any;
}

const VehicleInfo: React.FC<VehicleInfoProps> = ({ vehicleInfo }) => (
    <div className={`mt-4 p-4 border rounded ${vehicleInfo.isValid ? 'bg-green-100 border-green-200 text-green-600' : 'bg-red-100 border-red-200 text-red-600'}`}>
        <h3 className="text-lg font-semibold">Vehicle Info:</h3>
        <p>License Plate: {vehicleInfo.license}</p>
        <p>Status: {vehicleInfo.isValid ? 'Valid' : 'Invalid'}</p>
        {vehicleInfo.isValid && (
            <p>Valid until: {vehicleInfo.validUntil}</p>
        )}
    </div>
);

export default VehicleInfo;
